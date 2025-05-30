package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.permanent.token.GoblinToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GeneralKreatTheBoltbringer extends CardImpl {

    public GeneralKreatTheBoltbringer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever one or more Goblins you control attack, create a 1/1 red Goblin creature token that's tapped and attacking.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new CreateTokenEffect(new GoblinToken(), 1, true, true),
                1, StaticFilters.FILTER_PERMANENT_CREATURE_GOBLINS
        ).setTriggerPhrase("Whenever one or more Goblins you control attack, "));

        // Whenever another creature you control enters, General Kreat, the Boltbringer deals 1 damage to each opponent.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new DamagePlayersEffect(1, TargetController.OPPONENT),
                StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL
        ));
    }

    private GeneralKreatTheBoltbringer(final GeneralKreatTheBoltbringer card) {
        super(card);
    }

    @Override
    public GeneralKreatTheBoltbringer copy() {
        return new GeneralKreatTheBoltbringer(this);
    }
}
