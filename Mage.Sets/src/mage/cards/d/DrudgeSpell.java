package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.SkeletonRegenerateToken;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author fireshoes
 */
public final class DrudgeSpell extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Skeleton tokens");

    static {
        filter.add(SubType.SKELETON.getPredicate());
        filter.add(TokenPredicate.TRUE);
    }

    public DrudgeSpell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}{B}");

        // {B}, Exile two creature cards from your graveyard: Create a 1/1 black Skeleton creature token. It has "{B}: Regenerate this creature."
        Effect effect = new CreateTokenEffect(new SkeletonRegenerateToken());
        effect.setText("create a 1/1 black Skeleton creature token. It has \"{B}: Regenerate this creature.\"");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ColoredManaCost(ColoredManaSymbol.B));
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(2, 2, StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD)));
        this.addAbility(ability);

        // When Drudge Spell leaves the battlefield, destroy all Skeleton tokens. They can't be regenerated.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new DestroyAllEffect(filter, true), false));
    }

    private DrudgeSpell(final DrudgeSpell card) {
        super(card);
    }

    @Override
    public DrudgeSpell copy() {
        return new DrudgeSpell(this);
    }
}
