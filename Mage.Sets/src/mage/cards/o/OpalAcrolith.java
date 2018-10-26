package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.SourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.DependencyType;
import mage.constants.Duration;
import mage.constants.Layer;
import static mage.constants.Layer.TypeChangingEffects_4;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TokenImpl;

/**
 *
 * @author jeffwadsworth
 */
public final class OpalAcrolith extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("creature spell");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public OpalAcrolith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Whenever an opponent casts a creature spell, if Opal Acrolith is an enchantment, Opal Acrolith becomes a 2/4 Soldier creature.
        TriggeredAbility ability = new SpellCastOpponentTriggeredAbility(new BecomesCreatureSourceEffect(new OpalAcrolithSoldier(), "", Duration.WhileOnBattlefield, true, false),
                filter, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, new SourceMatchesFilterCondition(StaticFilters.FILTER_ENCHANTMENT_PERMANENT),
                "Whenever an opponent casts a creature spell, if Opal Acrolith is an enchantment, Opal Acrolith becomes a 2/4 Soldier creature."));

        // {0}: Opal Acrolith becomes an enchantment.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesEnchantmentSourceEffect(CardType.ENCHANTMENT), new ManaCostsImpl("{0}")));

    }

    public OpalAcrolith(final OpalAcrolith card) {
        super(card);
    }

    @Override
    public OpalAcrolith copy() {
        return new OpalAcrolith(this);
    }
}

class BecomesEnchantmentSourceEffect extends ContinuousEffectImpl implements SourceEffect {

    public BecomesEnchantmentSourceEffect(CardType cardType) {
        super(Duration.Custom, Outcome.AddAbility);
        staticText = "Opal Acrolith becomes an Enchantment";
        dependencyTypes.add(DependencyType.EnchantmentAddingRemoving);

    }

    public BecomesEnchantmentSourceEffect(final BecomesEnchantmentSourceEffect effect) {
        super(effect);
    }

    @Override
    public BecomesEnchantmentSourceEffect copy() {
        return new BecomesEnchantmentSourceEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        affectedObjectList.add(new MageObjectReference(source.getSourceId(), game));
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = affectedObjectList.get(0).getPermanent(game);
        if (permanent != null) {
            switch (layer) {
                case TypeChangingEffects_4:
                    if (sublayer == SubLayer.NA) {
                        permanent.getCardType().clear();
                        permanent.getSubtype(game).clear();
                        if (!permanent.getCardType().contains(CardType.ENCHANTMENT)) {
                            permanent.getCardType().add(CardType.ENCHANTMENT);
                        }
                    }
                    break;
            }
            return true;
        }
        this.discard();
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return Layer.TypeChangingEffects_4 == layer;
    }

}

class OpalAcrolithSoldier extends TokenImpl {

    public OpalAcrolithSoldier() {
        super("Soldier", "2/4 Ape creature");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(2);
        toughness = new MageInt(4);
    }

    public OpalAcrolithSoldier(final OpalAcrolithSoldier token) {
        super(token);
    }

    public OpalAcrolithSoldier copy() {
        return new OpalAcrolithSoldier(this);
    }
}
