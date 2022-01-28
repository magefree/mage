package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class NightfallPredator extends CardImpl {

    public NightfallPredator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.WEREWOLF);

        this.color.setGreen(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // {R}, {tap}: Nightfall Predator fights target creature.
        Ability activatedAbility = new SimpleActivatedAbility(
                new FightTargetSourceEffect().setText(
                        "{this} fights target creature. " +
                        "<i>(Each deals damage equal to its power to the other.)</i>"),
                new ManaCostsImpl("{R}")
        );
        activatedAbility.addCost(new TapSourceCost());
        activatedAbility.addTarget(new TargetCreaturePermanent());
        this.addAbility(activatedAbility);

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Nightfall Predator.
        this.addAbility(new WerewolfBackTriggeredAbility());
    }

    private NightfallPredator(final NightfallPredator card) {
        super(card);
    }

    @Override
    public NightfallPredator copy() {
        return new NightfallPredator(this);
    }
}
