package mage.cards.a;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.MagecraftAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.IfAbilityHasResolvedXTimesEffect;
import mage.abilities.effects.common.continuous.YouDontLoseManaEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.watchers.common.AbilityResolvedWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class AshlingFlameDancer extends CardImpl {

    public AshlingFlameDancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // You don't lose unspent red mana as steps and phases end.
        this.addAbility(new SimpleStaticAbility(new YouDontLoseManaEffect(ManaType.RED)));

        // Magecraft -- Whenever you cast or copy an instant or sorcery spell, discard a card, then draw a card. If this is the second time this ability has resolved this turn, Ashling, Flame Dancer deals 2 damage to each opponent and each creature they control. If it's the third time, add {R}{R}{R}{R}.
        Ability ability = new MagecraftAbility(new DiscardControllerEffect(1));
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));
        ability.addEffect(new IfAbilityHasResolvedXTimesEffect(
                Outcome.Damage, 2,
                new DamagePlayersEffect(2, TargetController.OPPONENT),
                new DamageAllEffect(2, StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE)
        ));
        ability.addEffect(new IfAbilityHasResolvedXTimesEffect(
                3, new BasicManaEffect(Mana.RedMana(4))
        ));
        this.addAbility(ability, new AbilityResolvedWatcher());
    }

    private AshlingFlameDancer(final AshlingFlameDancer card) {
        super(card);
    }

    @Override
    public AshlingFlameDancer copy() {
        return new AshlingFlameDancer(this);
    }
}
