package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ruleModifying.LegendRuleDoesntApplyEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.CastFromZonePredicate;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.util.functions.StackObjectCopyApplier;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class SpiderVerse extends CardImpl {

    private static final FilterControlledCreaturePermanent creatureFilter = new FilterControlledCreaturePermanent("Spiders you control");

    private static final FilterSpell spellFilter = new FilterSpell("a spell from anywhere other than your hand");

    static {
        creatureFilter.add(SubType.SPIDER.getPredicate());
        spellFilter.add(Predicates.not(new CastFromZonePredicate(Zone.HAND)));
    }

    public SpiderVerse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{R}");
        

        // The "legend rule" doesn't apply to Spiders you control.
        this.addAbility(new SimpleStaticAbility(new LegendRuleDoesntApplyEffect(creatureFilter)));

        // Whenever you cast a spell from anywhere other than your hand, you may copy it. If you do, you may choose new targets for the copy. If the copy is a permanent spell, it gains haste. Do this only once each turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new SpiderVerseEffect(),
                true
                ).setDoOnlyOnceEachTurn(true)
        );
    }

    private SpiderVerse(final SpiderVerse card) {
        super(card);
    }

    @Override
    public SpiderVerse copy() {
        return new SpiderVerse(this);
    }
}

class SpiderVerseEffect extends OneShotEffect {

    SpiderVerseEffect() {
        super(Outcome.Benefit);
        staticText = "copy it. If you do, you may choose new targets for the copy. " +
                "If the copy is a permanent spell, it gains haste";
    }

    protected SpiderVerseEffect(final SpiderVerseEffect effect) {
        super(effect);
    }

    @Override
    public SpiderVerseEffect copy() {
        return new SpiderVerseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) getValue("spellCast");
        if (spell == null) {
            return false;
        }
        spell.createCopyOnStack(
                game, source, source.getControllerId(), true,
                1, SpiderVerseCopyApplier.instance);
        return true;
    }
}

enum SpiderVerseCopyApplier implements StackObjectCopyApplier {
    instance;

    @Override
    public void modifySpell(StackObject stackObject, Game game) {
        if (!stackObject.isPermanent(game)) {
            return;
        }
        Spell spell = (Spell) stackObject;
        spell.addAbilityForCopy(HasteAbility.getInstance());
    }

    @Override
    public MageObjectReferencePredicate getNextNewTargetType() {
        return null;
    }
}