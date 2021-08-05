package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Flumph extends CardImpl {

    public Flumph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.JELLYFISH);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Flumph is dealt damage, you and target opponent each draw a card.
        Ability ability = new DealtDamageToSourceTriggeredAbility(
                new DrawCardSourceControllerEffect(1).setText("you"), false
        );
        ability.addEffect(new DrawCardTargetEffect(1).setText("and target opponent each draw a card"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private Flumph(final Flumph card) {
        super(card);
    }

    @Override
    public Flumph copy() {
        return new Flumph(this);
    }
}
