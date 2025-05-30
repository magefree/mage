package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FirespitterWhelp extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a noncreature or Dragon spell");

    static {
        filter.add(Predicates.or(
                Predicates.not(CardType.CREATURE.getPredicate()),
                SubType.DRAGON.getPredicate()
        ));
    }

    public FirespitterWhelp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a noncreature or Dragon spell, Firespitter Whelp deals 1 damage to each opponent.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DamagePlayersEffect(1, TargetController.OPPONENT), filter, false
        ));
    }

    private FirespitterWhelp(final FirespitterWhelp card) {
        super(card);
    }

    @Override
    public FirespitterWhelp copy() {
        return new FirespitterWhelp(this);
    }
}
