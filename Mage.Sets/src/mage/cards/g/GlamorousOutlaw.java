package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.GiveManaAbilityAndCastSourceAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GlamorousOutlaw extends CardImpl {

    public GlamorousOutlaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}{R}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // When Glamorous Outlaw enters the battlefield, it deals 2 damage to each opponent and you scry 2.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new DamagePlayersEffect(2, TargetController.OPPONENT)
        );
        ability.addEffect(new ScryEffect(2, false).concatBy("and you"));
        this.addAbility(ability);

        // {2}, Exile Glamorous Outlaw from your hand: Target land gains "{T}: Add {U}, {B}, or {R}" until Glamorous Outlaw is cast from exile. You may cast Glamorous Outlaw for as long as it remains exiled.
        this.addAbility(new GiveManaAbilityAndCastSourceAbility("UBR"));
    }

    private GlamorousOutlaw(final GlamorousOutlaw card) {
        super(card);
    }

    @Override
    public GlamorousOutlaw copy() {
        return new GlamorousOutlaw(this);
    }
}
