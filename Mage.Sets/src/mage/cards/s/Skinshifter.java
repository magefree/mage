
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.permanent.token.TokenImpl;

/**
 *
 * @author North
 */
public final class Skinshifter extends CardImpl {

    public Skinshifter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        Ability ability = new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD,
                new BecomesCreatureSourceEffect(new RhinoToken(), "", Duration.EndOfTurn),
                new ManaCostsImpl("{G}"));

        Mode mode = new Mode();
        mode.addEffect(new BecomesCreatureSourceEffect(new BirdToken(), "", Duration.EndOfTurn));
        ability.addMode(mode);

        mode = new Mode();
        mode.addEffect(new BecomesCreatureSourceEffect(new PlantToken(), "", Duration.EndOfTurn));
        ability.addMode(mode);

        this.addAbility(ability);
    }

    public Skinshifter(final Skinshifter card) {
        super(card);
    }

    @Override
    public Skinshifter copy() {
        return new Skinshifter(this);
    }

    private class RhinoToken extends TokenImpl {

        public RhinoToken() {
            super("Rhino", "Rhino with base power and toughness 4/4 and gains trample");
            this.cardType.add(CardType.CREATURE);
            this.subtype.add(SubType.RHINO);

            this.color.setGreen(true);
            this.power = new MageInt(4);
            this.toughness = new MageInt(4);
            this.addAbility(TrampleAbility.getInstance());
        }
        public RhinoToken(final RhinoToken token) {
            super(token);
        }

        public RhinoToken copy() {
            return new RhinoToken(this);
        }
    }

    private class BirdToken extends TokenImpl {

        public BirdToken() {
            super("Bird", "Bird with base power and toughness 2/2 and gains flying");
            this.cardType.add(CardType.CREATURE);
            this.subtype.add(SubType.BIRD);

            this.color.setGreen(true);
            this.power = new MageInt(2);
            this.toughness = new MageInt(2);
            this.addAbility(FlyingAbility.getInstance());
        }
        public BirdToken(final BirdToken token) {
            super(token);
        }

        public BirdToken copy() {
            return new BirdToken(this);
        }
    }

    private class PlantToken extends TokenImpl {

        public PlantToken() {
            super("Plant", "Plant with base power and toughness 0/8");
            this.cardType.add(CardType.CREATURE);
            this.subtype.add(SubType.PLANT);

            this.color.setGreen(true);
            this.power = new MageInt(0);
            this.toughness = new MageInt(8);
        }
        public PlantToken(final PlantToken token) {
            super(token);
        }

        public PlantToken copy() {
            return new PlantToken(this);
        }
    }
}
