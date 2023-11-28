package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInOpponentsGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DisruptorWanderglyph extends CardImpl {

    private static final FilterCard filter = new FilterCard("card from an opponent's graveyard");

    public DisruptorWanderglyph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever Disruptor Wanderglyph attacks, exile target card from an opponent's graveyard.
        Ability ability = new AttacksTriggeredAbility(new ExileTargetEffect());
        ability.addTarget(new TargetCardInOpponentsGraveyard(filter));
        this.addAbility(ability);
    }

    private DisruptorWanderglyph(final DisruptorWanderglyph card) {
        super(card);
    }

    @Override
    public DisruptorWanderglyph copy() {
        return new DisruptorWanderglyph(this);
    }
}
