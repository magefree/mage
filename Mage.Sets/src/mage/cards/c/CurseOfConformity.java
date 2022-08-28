package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CurseOfConformity extends CardImpl {

    public CurseOfConformity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}");

        this.subtype.add(SubType.AURA);
        this.subtype.add(SubType.CURSE);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Nonlegendary creatures enchanted player controls have base power and toughness 3/3 and lose all creature types.
        this.addAbility(new SimpleStaticAbility(new CurseOfConformityEffect()));
    }

    private CurseOfConformity(final CurseOfConformity card) {
        super(card);
    }

    @Override
    public CurseOfConformity copy() {
        return new CurseOfConformity(this);
    }
}

class CurseOfConformityEffect extends ContinuousEffectImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
        filter.add(TargetController.ENCHANTED.getControllerPredicate());
    }

    CurseOfConformityEffect() {
        super(Duration.WhileOnBattlefield, Outcome.LoseAbility);
        staticText = "nonlegendary creatures enchanted player controls " +
                "have base power and toughness 3/3 and lose all creature types";
    }

    private CurseOfConformityEffect(final CurseOfConformityEffect effect) {
        super(effect);
    }

    @Override
    public CurseOfConformityEffect copy() {
        return new CurseOfConformityEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                filter, source.getControllerId(), source, game
        )) {
            switch (layer) {
                case TypeChangingEffects_4:
                    permanent.removeAllCreatureTypes(game);
                    break;
                case PTChangingEffects_7:
                    if (sublayer == SubLayer.SetPT_7b) {
                        permanent.getPower().setModifiedBaseValue(3);
                        permanent.getToughness().setModifiedBaseValue(3);
                    }
            }
        }
        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4 || layer == Layer.PTChangingEffects_7;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }
}
