package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SuspectTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JJonahJameson extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature you control with menace");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(new AbilityPredicate(MenaceAbility.class));
    }

    public JJonahJameson(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When J. Jonah Jameson enters, suspect up to one target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SuspectTargetEffect());
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // Whenever a creature you control with menace attacks, create a Treasure token.
        this.addAbility(new AttacksAllTriggeredAbility(
                new CreateTokenEffect(new TreasureToken()), false,
                filter, SetTargetPointer.NONE, false
        ));
    }

    private JJonahJameson(final JJonahJameson card) {
        super(card);
    }

    @Override
    public JJonahJameson copy() {
        return new JJonahJameson(this);
    }
}
