package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.MayTapOrUntapTargetEffect;
import mage.abilities.keyword.DisguiseAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GraniteWitness extends CardImpl {

    public GraniteWitness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{W}{U}");

        this.subtype.add(SubType.GARGOYLE);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Disguise {W/U}{W/U}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{W/U}{W/U}")));

        // When Granite Witness is turned face up, you may tap or untap target creature.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new MayTapOrUntapTargetEffect());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private GraniteWitness(final GraniteWitness card) {
        super(card);
    }

    @Override
    public GraniteWitness copy() {
        return new GraniteWitness(this);
    }
}
