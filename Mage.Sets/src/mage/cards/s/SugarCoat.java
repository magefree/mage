package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.token.FoodAbility;
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
 * @author notgreat
 */
public final class SugarCoat extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature or Food");

    static {
        filter.add(Predicates.or(SubType.FOOD.getPredicate(), CardType.CREATURE.getPredicate()));
    }

    public SugarCoat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant creature or Food
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.UnboostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted permanent is a colorless Food artifact with "{2}, {T}, Sacrifice this artifact: You gain 3 life" and loses all other card types and abilities.
        this.addAbility(new SimpleStaticAbility(new SugarCoatEffect()));
    }

    private SugarCoat(final SugarCoat card) {
        super(card);
    }

    @Override
    public SugarCoat copy() {
        return new SugarCoat(this);
    }
}

// Based on MinimusContainmentEffect
class SugarCoatEffect extends ContinuousEffectImpl {

    private static final Ability ability = new FoodAbility();

    SugarCoatEffect() {
        super(Duration.WhileOnBattlefield, Outcome.LoseAbility);
        staticText = "Enchanted permanent is a colorless Food artifact with " +
                "\"{2}, {T}, Sacrifice this artifact: You gain 3 life\" and loses all other card types and abilities";
    }

    private SugarCoatEffect(final SugarCoatEffect effect) {
        super(effect);
    }

    @Override
    public SugarCoatEffect copy() {
        return new SugarCoatEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent aura = source.getSourcePermanentIfItStillExists(game);
        if (aura == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(aura.getAttachedTo());
        if (permanent == null) {
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                permanent.retainAllArtifactSubTypes(game);
                permanent.removeAllCardTypes(game);
                permanent.addCardType(game, CardType.ARTIFACT);
                permanent.addSubType(game, SubType.FOOD);
                return true;
            case AbilityAddingRemovingEffects_6:
                permanent.removeAllAbilities(source.getSourceId(), game);
                permanent.addAbility(ability, source.getSourceId(), game);
                return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4 || layer == Layer.AbilityAddingRemovingEffects_6;
    }
}
