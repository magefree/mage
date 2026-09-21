package mage.cards.c;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyStackObjectEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.PreparedSpellCopy;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.filter.StaticFilters;

/**
 *
 * @author muz
 */
public final class CodieRavenousCodex extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a prepared spell");

    static {
        filter.add(PreparedSpellPredicate.INSTANCE);
    }

    public CodieRavenousCodex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BOOK);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Whenever you cast a prepared spell, copy it. You may choose new targets for the copy.
        this.addAbility(new SpellCastControllerTriggeredAbility(
            new CopyStackObjectEffect("it"), filter, false, SetTargetPointer.SPELL
        ));

        // {W}{U}{B}{R}{G}, {T}: Each creature you control becomes prepared.
        Ability ability = new SimpleActivatedAbility(
            new CodieRavenousCodexPrepareEffect(),
            new ManaCostsImpl<>("{W}{U}{B}{R}{G}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private CodieRavenousCodex(final CodieRavenousCodex card) {
        super(card);
    }

    @Override
    public CodieRavenousCodex copy() {
        return new CodieRavenousCodex(this);
    }
}

enum PreparedSpellPredicate implements ObjectSourcePlayerPredicate<StackObject> {
    INSTANCE;

    @Override
    public boolean apply(ObjectSourcePlayer<StackObject> input, Game game) {
        return input.getObject() instanceof Spell && ((Spell) input.getObject()).getCard() instanceof PreparedSpellCopy;
    }
}

class CodieRavenousCodexPrepareEffect extends OneShotEffect {

    CodieRavenousCodexPrepareEffect() {
        super(Outcome.Benefit);
        staticText = "each creature you control becomes prepared";
    }

    private CodieRavenousCodexPrepareEffect(final CodieRavenousCodexPrepareEffect effect) {
        super(effect);
    }

    @Override
    public CodieRavenousCodexPrepareEffect copy() {
        return new CodieRavenousCodexPrepareEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_CONTROLLED_CREATURES, source.getControllerId(), game)) {
            permanent.setPrepared(true, game);
        }
        return true;
    }
}
