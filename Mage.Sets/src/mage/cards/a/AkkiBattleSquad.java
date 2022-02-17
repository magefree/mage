package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.effects.common.AdditionalCombatPhaseEffect;
import mage.abilities.effects.common.UntapAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.ModifiedPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AkkiBattleSquad extends CardImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("modified creatures you control");

    static {
        filter.add(ModifiedPredicate.instance);
    }

    public AkkiBattleSquad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Whenever one or more modified creatures you control attack, untap all modified creatures you control. After this combat phase, there is an additional combat phase. This ability triggers only once each turn.
        Ability ability = new AttacksCreatureYouControlTriggeredAbility(
                new UntapAllEffect(filter), false, filter
        ).setTriggerPhrase("Whenever one or more modified creatures you control attack, ").setTriggersOnce(true);
        ability.addEffect(new AdditionalCombatPhaseEffect());
        this.addAbility(ability);
    }

    private AkkiBattleSquad(final AkkiBattleSquad card) {
        super(card);
    }

    @Override
    public AkkiBattleSquad copy() {
        return new AkkiBattleSquad(this);
    }
}
