package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.IfAbilityHasResolvedXTimesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.watchers.common.AbilityResolvedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TannukMemorialEnsign extends CardImpl {

    public TannukMemorialEnsign(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KAVU);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Landfall - Whenever a land you control enters, Tannuk deals 1 damage to each opponent. If this is the second time this ability has resolved this turn, draw a card.
        Ability ability = new LandfallAbility(new DamagePlayersEffect(1, TargetController.OPPONENT));
        ability.addEffect(new IfAbilityHasResolvedXTimesEffect(2, new DrawCardSourceControllerEffect(1)));
        this.addAbility(ability, new AbilityResolvedWatcher());
    }

    private TannukMemorialEnsign(final TannukMemorialEnsign card) {
        super(card);
    }

    @Override
    public TannukMemorialEnsign copy() {
        return new TannukMemorialEnsign(this);
    }
}
