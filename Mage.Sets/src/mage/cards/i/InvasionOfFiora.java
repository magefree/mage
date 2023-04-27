package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.m.MarchesaResoluteMonarch;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfFiora extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("legendary creatures");
    private static final FilterPermanent filter2 = new FilterCreaturePermanent("nonlegendary creatures");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
        filter2.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    public InvasionOfFiora(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.BATTLE}, "{4}{B}{B}");

        this.subtype.add(SubType.SIEGE);
        this.setStartingDefense(4);
        this.secondSideCardClazz = mage.cards.m.MarchesaResoluteMonarch.class;

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.addAbility(new SiegeAbility());

        // When Invasion of Fiora enters the battlefield, choose one or both --
        // * Destroy all legendary creatures.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyAllEffect(filter));
        ability.getModes().setMinModes(1);
        ability.getModes().setMaxModes(2);

        // * Destroy all nonlegendary creatures.
        ability.addMode(new Mode(new DestroyAllEffect(filter2)));
        this.addAbility(ability, MarchesaResoluteMonarch.makeWatcher());
    }

    private InvasionOfFiora(final InvasionOfFiora card) {
        super(card);
    }

    @Override
    public InvasionOfFiora copy() {
        return new InvasionOfFiora(this);
    }
}
