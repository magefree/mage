package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.TotemArmorAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TreefolkUmbra extends CardImpl {

    public TreefolkUmbra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +0/+2 and assigns combat damage equal to its toughness rather than its power.
        ability = new SimpleStaticAbility(new BoostEnchantedEffect(0, 2));
        ability.addEffect(new TreefolkUmbraEffect());
        this.addAbility(ability);

        // Totem armor
        this.addAbility(new TotemArmorAbility());
    }

    private TreefolkUmbra(final TreefolkUmbra card) {
        super(card);
    }

    @Override
    public TreefolkUmbra copy() {
        return new TreefolkUmbra(this);
    }
}

class TreefolkUmbraEffect extends ContinuousEffectImpl {

    TreefolkUmbraEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "and assigns combat damage equal to its toughness rather than its power";
    }

    private TreefolkUmbraEffect(final TreefolkUmbraEffect effect) {
        super(effect);
    }

    @Override
    public TreefolkUmbraEffect copy() {
        return new TreefolkUmbraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null || permanent.getAttachedTo() == null) {
            return false;
        }
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new PermanentIdPredicate(permanent.getAttachedTo()));
        game.getCombat().setUseToughnessForDamage(true);
        game.getCombat().addUseToughnessForDamageFilter(filter);
        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}
