package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ZombieDruidToken;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheSibsigCeremony extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature spells");
    private static final FilterPermanent filter2 = new FilterControlledCreaturePermanent();

    static {
        filter2.add(TheSibsigCeremonyPredicate.instance);
    }

    public TheSibsigCeremony(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);

        // Creature spells you cast cost {2} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 2)));

        // Whenever a creature you control enters, if you cast it, destroy that creature, then create a 2/2 black Zombie Druid creature token.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD, new DestroyTargetEffect()
                .setText("if you cast it, destroy that creature"),
                filter2, false, SetTargetPointer.PERMANENT
        );
        ability.addEffect(new CreateTokenEffect(new ZombieDruidToken()).concatBy(", then"));
        this.addAbility(ability);
    }

    private TheSibsigCeremony(final TheSibsigCeremony card) {
        super(card);
    }

    @Override
    public TheSibsigCeremony copy() {
        return new TheSibsigCeremony(this);
    }
}

enum TheSibsigCeremonyPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        int zcc = input.getZoneChangeCounter(game);
        Spell spell = game.getStack().getSpell(input.getId());
        return (spell != null && spell.getZoneChangeCounter(game) == zcc - 1)
                || game.getLastKnownInformation(input.getId(), Zone.STACK, zcc - 1) != null;
    }
}
