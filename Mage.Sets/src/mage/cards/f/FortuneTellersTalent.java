package mage.cards.f;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainClassAbilitySourceEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.continuous.PlayFromTopOfLibraryEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.ClassLevelAbility;
import mage.abilities.keyword.ClassReminderAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.other.SpellCastFromAnywhereOtherThanHand;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.watchers.common.SpellsCastWatcher;

/**
 *
 * @author jimga150
 */
public final class FortuneTellersTalent extends CardImpl {

    private static final FilterCard nonHandSpellFilter = new FilterCard("Spells you cast from anywhere other than your hand");

    static {
        nonHandSpellFilter.add(SpellCastFromAnywhereOtherThanHand.instance);
    }

    public FortuneTellersTalent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");
        
        this.subtype.add(SubType.CLASS);

        // (Gain the next level as a sorcery to add its ability.)
        this.addAbility(new ClassReminderAbility());

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // {3}{U}: Level 2
        this.addAbility(new ClassLevelAbility(2, "{3}{U}"));

        // As long as you've cast a spell this turn, you may play cards from the top of your library.
        Effect lv2Effect = new ConditionalAsThoughEffect(
                new PlayFromTopOfLibraryEffect(),
                FortuneTellersTalentCondition.instance
        );
        lv2Effect.setText("As long as you've cast a spell this turn, you may play cards from the top of your library.");
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(new SimpleStaticAbility(lv2Effect), 2)));

        // {2}{U}: Level 3
        this.addAbility(new ClassLevelAbility(3, "{2}{U}"));

        // Spells you cast from anywhere other than your hand cost {2} less to cast.
        Ability lv3Ability = new SimpleStaticAbility(new SpellsCostReductionControllerEffect(nonHandSpellFilter, 2));
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(lv3Ability, 3)));
    }

    private FortuneTellersTalent(final FortuneTellersTalent card) {
        super(card);
    }

    @Override
    public FortuneTellersTalent copy() {
        return new FortuneTellersTalent(this);
    }
}

// Based on LeapfrogCondition
enum FortuneTellersTalentCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        if (watcher == null) {
            return false;
        }
        List<Spell> spells = watcher.getSpellsCastThisTurn(source.getControllerId());
        return spells != null && spells
                .stream()
                .anyMatch(Objects::nonNull);
    }
}
