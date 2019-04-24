
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class ImprisonedInTheMoon extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature, land, or planeswalker");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.LAND),
                new CardTypePredicate(CardType.PLANESWALKER)));
    }

    public ImprisonedInTheMoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature, land, or planeswalker
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted permanent is a colorless land with "{T}: Add {C}" and loses all other card types and abilities.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BecomesColorlessLandEffect()));
    }

    public ImprisonedInTheMoon(final ImprisonedInTheMoon card) {
        super(card);
    }

    @Override
    public ImprisonedInTheMoon copy() {
        return new ImprisonedInTheMoon(this);
    }
}

class BecomesColorlessLandEffect extends ContinuousEffectImpl {

    public BecomesColorlessLandEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        this.staticText = "Enchanted permanent is a colorless land with \"{T}: Add {C}\" and loses all other card types and abilities";
    }

    public BecomesColorlessLandEffect(final BecomesColorlessLandEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public BecomesColorlessLandEffect copy() {
        return new BecomesColorlessLandEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Permanent permanent = game.getPermanent(enchantment.getAttachedTo());
            if (permanent != null) {
                switch (layer) {
                    case ColorChangingEffects_5:
                        permanent.getColor(game).setWhite(false);
                        permanent.getColor(game).setGreen(false);
                        permanent.getColor(game).setBlack(false);
                        permanent.getColor(game).setBlue(false);
                        permanent.getColor(game).setRed(false);
                        break;
                    case AbilityAddingRemovingEffects_6:
                        permanent.removeAllAbilities(source.getSourceId(), game);
                        permanent.addAbility(new ColorlessManaAbility(), source.getSourceId(), game);
                        break;
                    case TypeChangingEffects_4:
                        boolean isLand = permanent.isLand();
                        permanent.getCardType().clear();
                        permanent.addCardType(CardType.LAND);
                        if (!isLand) {
                            permanent.getSubtype(game).clear();
                        }
                        break;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.ColorChangingEffects_5 || layer == Layer.TypeChangingEffects_4;
    }
}
