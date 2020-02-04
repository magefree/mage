package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.FirstSpellOpponentsTurnTriggeredAbility;
import mage.abilities.effects.common.MayTapOrUntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StingingLionfish extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("nonland permanent");

    public StingingLionfish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.FISH);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever you cast your first spell during each opponent's turn, you may tap or untap target nonland permanent.
        Ability ability = new FirstSpellOpponentsTurnTriggeredAbility(
                new MayTapOrUntapTargetEffect(), false
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private StingingLionfish(final StingingLionfish card) {
        super(card);
    }

    @Override
    public StingingLionfish copy() {
        return new StingingLionfish(this);
    }
}
