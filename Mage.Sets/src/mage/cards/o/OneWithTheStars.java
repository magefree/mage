package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OneWithTheStars extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature or enchantment");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
    }

    public OneWithTheStars(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature or enchantment
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted permanent is an enchantment and loses all other card types.
        this.addAbility(new SimpleStaticAbility(new OneWithTheStarsEffect()));
    }

    private OneWithTheStars(final OneWithTheStars card) {
        super(card);
    }

    @Override
    public OneWithTheStars copy() {
        return new OneWithTheStars(this);
    }
}

class OneWithTheStarsEffect extends ContinuousEffectImpl {

    OneWithTheStarsEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Detriment);
        this.staticText = "Enchanted permanent is an enchantment and loses all other card types.";
    }

    private OneWithTheStarsEffect(final OneWithTheStarsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public OneWithTheStarsEffect copy() {
        return new OneWithTheStarsEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment == null || enchantment.getAttachedTo() == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(enchantment.getAttachedTo());
        if (permanent == null) {
            return false;
        }
        permanent.setIsAllCreatureTypes(game, false);
        permanent.retainAllEnchantmentSubTypes(game);
        permanent.removeAllCardTypes(game);
        permanent.addCardType(game, CardType.ENCHANTMENT);
        return true;
    }
}
