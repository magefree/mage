package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.ColorlessManaAbility;
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
 * @author fireshoes
 */
public final class ImprisonedInTheMoon extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature, land, or planeswalker");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    public ImprisonedInTheMoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature, land, or planeswalker
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted permanent is a colorless land with "{T}: Add {C}" and loses all other card types and abilities.
        this.addAbility(new SimpleStaticAbility(new ImprisonedInTheMoonEffect()));
    }

    private ImprisonedInTheMoon(final ImprisonedInTheMoon card) {
        super(card);
    }

    @Override
    public ImprisonedInTheMoon copy() {
        return new ImprisonedInTheMoon(this);
    }
}

class ImprisonedInTheMoonEffect extends ContinuousEffectImpl {

    ImprisonedInTheMoonEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        this.staticText = "Enchanted permanent is a colorless land " +
                "with \"{T}: Add {C}\" and loses all other card types and abilities";
    }

    private ImprisonedInTheMoonEffect(final ImprisonedInTheMoonEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public ImprisonedInTheMoonEffect copy() {
        return new ImprisonedInTheMoonEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent enchantment = source.getSourcePermanentIfItStillExists(game);
        if (enchantment == null
                || enchantment.getAttachedTo() == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(enchantment.getAttachedTo());
        if (permanent == null) {
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                // 305.7 Note that this doesn't remove any abilities that were granted to the land by other effects
                // So the ability removing has to be done before Layer 6
                permanent.removeAllAbilities(source.getSourceId(), game);
                permanent.removeAllCardTypes(game);
                permanent.addCardType(game, CardType.LAND);
                permanent.retainAllLandSubTypes(game);
                break;
            case ColorChangingEffects_5:
                permanent.getColor(game).setWhite(false);
                permanent.getColor(game).setBlue(false);
                permanent.getColor(game).setBlack(false);
                permanent.getColor(game).setRed(false);
                permanent.getColor(game).setGreen(false);
                break;
            case AbilityAddingRemovingEffects_6:
                permanent.removeAllAbilities(source.getSourceId(), game);
                permanent.addAbility(new ColorlessManaAbility(), source.getSourceId(), game);
                break;
        }
        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6
                || layer == Layer.ColorChangingEffects_5
                || layer == Layer.TypeChangingEffects_4;
    }
}
