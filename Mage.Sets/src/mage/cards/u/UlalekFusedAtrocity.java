package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ColorlessManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.util.CardUtil;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class UlalekFusedAtrocity extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an Eldrazi spell");

    static {
        filter.add(SubType.ELDRAZI.getPredicate());
    }

    public UlalekFusedAtrocity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{C/W}{C/U}{C/B}{C/R}{C/G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Whenever you cast an Eldrazi spell, you may pay {C}{C}. If you do, copy all spells you control, then copy all other activated and triggered abilities you control. You may choose new targets for the copies.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DoIfCostPaid(new UlalekFusedAtrocityEffect(), new ColorlessManaCost(2)), filter, false
        ));
    }

    private UlalekFusedAtrocity(final UlalekFusedAtrocity card) {
        super(card);
    }

    @Override
    public UlalekFusedAtrocity copy() {
        return new UlalekFusedAtrocity(this);
    }
}

class UlalekFusedAtrocityEffect extends OneShotEffect {

    UlalekFusedAtrocityEffect() {
        super(Outcome.Benefit);
        staticText = "copy all spells you control, then copy all other activated " +
                "and triggered abilities you control. You may choose new targets for the copies";
    }

    private UlalekFusedAtrocityEffect(final UlalekFusedAtrocityEffect effect) {
        super(effect);
    }

    @Override
    public UlalekFusedAtrocityEffect copy() {
        return new UlalekFusedAtrocityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Spell> spells = CardUtil.castStream(game.getStack().stream(), Spell.class)
                .filter(spell -> spell.isControlledBy(source.getControllerId()))
                .collect(Collectors.toList());
        for (Spell spell : spells) {
            spell.createCopyOnStack(game, source, source.getControllerId(), true);
        }
        List<StackObject> abilities = game
                .getStack()
                .stream()
                .filter(obj -> !(obj instanceof Spell))
                .filter(obj -> obj.isControlledBy(source.getControllerId()))
                .filter(obj -> !obj.getId().equals(source.getId()))
                .collect(Collectors.toList());
        for (StackObject obj : abilities) {
            obj.createCopyOnStack(game, source, source.getControllerId(), true);
        }
        return true;
    }
}
