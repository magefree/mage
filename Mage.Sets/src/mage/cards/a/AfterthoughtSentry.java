package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.SubType;
import mage.target.common.TargetCardInGraveyard;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class AfterthoughtSentry extends CardImpl {

    public AfterthoughtSentry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.GARGOYLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {2}: This creature gains flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(
            new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn),
            new ManaCostsImpl<>("{2}")
        ));

        // Whenever this creature attacks, exile up to one target card from a graveyard.
        Ability ability = new AttacksTriggeredAbility(new ExileTargetEffect());
        ability.addTarget(new TargetCardInGraveyard(0, 1));
        this.addAbility(ability);
    }

    private AfterthoughtSentry(final AfterthoughtSentry card) {
        super(card);
    }

    @Override
    public AfterthoughtSentry copy() {
        return new AfterthoughtSentry(this);
    }
}
