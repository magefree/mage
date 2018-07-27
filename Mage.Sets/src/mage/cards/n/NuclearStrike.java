package mage.cards.n;

import java.util.UUID;

import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.constants.*;
import mage.abilities.Ability;
import mage.abilities.effects.common.AttachEffect;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;

/**
 *
 * @author NinthWorld
 */
public final class NuclearStrike extends CardImpl {

    public NuclearStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");
        
        this.subtype.add(SubType.AURA);

        // Enchant nonland permanent
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_PERMANENT_NON_LAND);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // At the beginning of your upkeep, sacrifice Nuclear Strike. If you do, destroy enchanted permanent and each other nonland permanent with the same converted mana cost.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new NuclearStrikeEffect(), TargetController.YOU, false));
    }

    public NuclearStrike(final NuclearStrike card) {
        super(card);
    }

    @Override
    public NuclearStrike copy() {
        return new NuclearStrike(this);
    }
}

class NuclearStrikeEffect extends SacrificeSourceEffect {

    private boolean sacrificed = false;

    public NuclearStrikeEffect() {
        super();
        staticText = "sacrifice {this}. If you do, destroy enchanted permanent and each other nonland permanent with the same converted mana cost";
    }

    public NuclearStrikeEffect(final NuclearStrikeEffect effect) {
        super(effect);
        this.sacrificed = effect.sacrificed;
    }

    @Override
    public NuclearStrikeEffect copy() {
        return new NuclearStrikeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        sacrificed = super.apply(game, source);
        if (sacrificed) {
            Permanent enchantment = game.getPermanent(source.getSourceId());
            if(enchantment != null) {
                Permanent enchanted = game.getPermanent(enchantment.getAttachedTo());
                if(enchanted != null) {
                    int cmc = enchanted.getConvertedManaCost();

                    // destroy enchanted permanent
                    enchanted.destroy(source.getSourceId(), game, false);

                    // each other nonland permanent with the same converted mana cost
                    FilterNonlandPermanent filter = new FilterNonlandPermanent();
                    filter.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, cmc));
                    for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, game)) {
                        if(permanent != null && permanent != enchanted) {
                            permanent.destroy(source.getSourceId(), game, false);
                        }
                    }
                }
            }
        }
        return sacrificed;
    }

    public boolean isSacrificed() {
        return sacrificed;
    }
}