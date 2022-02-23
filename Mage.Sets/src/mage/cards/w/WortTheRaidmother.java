package mage.cards.w;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ConspireAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.token.GoblinWarriorToken;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class WortTheRaidmother extends CardImpl {

    public WortTheRaidmother(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R/G}{R/G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Wort, the Raidmother enters the battlefield, create two 1/1 red and green Goblin Warrior creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new GoblinWarriorToken(), 2), false));

        // Each red or green instant or sorcery spell you cast has conspire.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new WortGainConspireEffect()));
    }

    private WortTheRaidmother(final WortTheRaidmother card) {
        super(card);
    }

    @Override
    public WortTheRaidmother copy() {
        return new WortTheRaidmother(this);
    }
}

class WortGainConspireEffect extends ContinuousEffectImpl {

    private static final FilterInstantOrSorcerySpell filter = new FilterInstantOrSorcerySpell();

    static {
        filter.add(Predicates.or(new ColorPredicate(ObjectColor.RED), new ColorPredicate(ObjectColor.GREEN)));
    }

    public WortGainConspireEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "Each red or green instant or sorcery spell you cast has conspire. <i>(As you cast the spell, you may tap two untapped creatures you control that share a color with it. When you do, copy it and you may choose new targets for the copy.)</i>";
    }

    public WortGainConspireEffect(final WortGainConspireEffect effect) {
        super(effect);
    }

    @Override
    public WortGainConspireEffect copy() {
        return new WortGainConspireEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (StackObject stackObject : game.getStack()) {
            // only spells cast, so no copies of spells
            if ((!(stackObject instanceof Spell)) || stackObject.isCopy()
                    || !stackObject.isControlledBy(source.getControllerId())) {
                continue;
            }
            Spell spell = (Spell) stackObject;
            if (filter.match(stackObject, game)) {
                game.getState().addOtherAbility(spell.getCard(), new ConspireAbility(ConspireAbility.ConspireTargets.MORE));
            }
        }
        return true;
    }
}
