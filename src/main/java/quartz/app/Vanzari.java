package quartz.app;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Vanzari {
  private String agent;
  private String sumaContractePerAgent;
  private String nrContractePerAgent;
  private String luna;
  private double sumaTotalaPerAgent;
  private String diferentaRecalculariPerAgent;
  private String nrRecalculariPerAgent;
  private String nrOferte;
  private double sumaOferteTf;
  private String nrOferteTf;
  private double sumaAccesorii;
  private double sumaDiferentaAccesorii;

}
