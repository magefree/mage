package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.util.functions.StackObjectCopyApplier;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class SalinaeTheOceanMother extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an artifact or creature spell");

    static {
        filter.add(Predicates.or(
            CardType.ARTIFACT.getPredicate(),
            CardType.CREATURE.getPredicate()
        ));
    }

    public SalinaeTheOceanMother(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.WHALE);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // This spell costs {X} less to cast, where X is your devotion to blue.
        this.addAbility(new SimpleStaticAbility(
            Zone.ALL, new SpellCostReductionSourceEffect(DevotionCount.U)
        ).setRuleAtTheTop(true).addHint(DevotionCount.U.getHint()));

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever you cast an artifact or creature spell, copy it, except the copy is a 1/1 Fish creature in addition to its other types.
        this.addAbility(new SpellCastControllerTriggeredAbility(
            new SalinaeTheOceanMotherEffect(), filter, false
        ));
    }

    private SalinaeTheOceanMother(final SalinaeTheOceanMother card) {
        super(card);
    }

    @Override
    public SalinaeTheOceanMother copy() {
        return new SalinaeTheOceanMother(this);
    }
}

class SalinaeTheOceanMotherEffect extends OneShotEffect {

    SalinaeTheOceanMotherEffect() {
        super(Outcome.Copy);
        staticText = "copy it, except the copy is a 1/1 Fish creature in addition to its other types";
    }

    private SalinaeTheOceanMotherEffect(final SalinaeTheOceanMotherEffect effect) {
        super(effect);
    }

    @Override
    public SalinaeTheOceanMotherEffect copy() {
        return new SalinaeTheOceanMotherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Spell spell = (Spell) getValue("spellCast");
        if (controller == null || spell == null) {
            return false;
        }
        Spell spellOnStack = game.getStack().getSpell(spell.getId());
        if (spellOnStack == null || spellOnStack.getZoneChangeCounter(game) != spell.getZoneChangeCounter(game)) {
            return false;
        }
        spellOnStack.createCopyOnStack(game, source, controller.getId(), false, 1, SalinaeTheOceanMotherApplier.instance);
        return true;
    }
}

enum SalinaeTheOceanMotherApplier implements StackObjectCopyApplier {
    instance;

    @Override
    public void modifySpell(StackObject copiedSpell, Game game) {
        copiedSpell.addCardType(CardType.CREATURE);
        copiedSpell.addSubType(SubType.FISH);
        copiedSpell.getPower().setModifiedBaseValue(1);
        copiedSpell.getToughness().setModifiedBaseValue(1);
        Ability ability = new SimpleStaticAbility(new SetBasePowerToughnessSourceEffect(1, 1, Duration.Custom));
        ability.setRuleVisible(false);
        copiedSpell.getAbilities().add(ability);
    }

    @Override
    public MageObjectReferencePredicate getNextNewTargetType() {
        return null;
    }
}
