package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KamiOfRestlessShadows extends CardImpl {

    private static final FilterCard filter
            = new FilterCreatureCard("Ninja or Rogue creature card from your graveyard");

    static {
        filter.add(Predicates.or(
                SubType.NINJA.getPredicate(),
                SubType.ROGUE.getPredicate()
        ));
    }

    public KamiOfRestlessShadows(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Kami of Restless Shadows enters the battlefield, choose one —
        // • Return up to one target Ninja or Rogue creature card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, filter));

        // • Put target creature card from your graveyard on top of your library.
        Mode mode = new Mode(new PutOnLibraryTargetEffect(true));
        mode.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private KamiOfRestlessShadows(final KamiOfRestlessShadows card) {
        super(card);
    }

    @Override
    public KamiOfRestlessShadows copy() {
        return new KamiOfRestlessShadows(this);
    }
}
