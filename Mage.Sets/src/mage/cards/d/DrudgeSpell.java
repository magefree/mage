
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.SkeletonToken;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author fireshoes
 */
public final class DrudgeSpell extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Skeleton tokens");

    static {
        filter.add(new SubtypePredicate(SubType.SKELETON));
        filter.add(new TokenPredicate());
    }

    public DrudgeSpell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}{B}");

        // {B}, Exile two creature cards from your graveyard: Create a 1/1 black Skeleton creature token. It has "{B}: Regenerate this creature."
        Effect effect = new CreateTokenEffect(new SkeletonToken());
        effect.setText("create a 1/1 black Skeleton creature token. It has \"{B}: Regenerate this creature.\"");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{B}"));
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(2, 2, new FilterCreatureCard("creature cards from your graveyard"))));
        this.addAbility(ability);

        // When Drudge Spell leaves the battlefield, destroy all Skeleton tokens. They can't be regenerated.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new DestroyAllEffect(filter, true), false));
    }

    public DrudgeSpell(final DrudgeSpell card) {
        super(card);
    }

    @Override
    public DrudgeSpell copy() {
        return new DrudgeSpell(this);
    }
}
