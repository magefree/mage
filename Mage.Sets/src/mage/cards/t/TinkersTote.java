package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.GnomeToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TinkersTote extends CardImpl {

    public TinkersTote(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{W}");

        // When Tinker's Tote enters the battlefield, create two 1/1 colorless Gnome artifact creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new GnomeToken(), 2)));

        // {W}, Sacrifice Tinker's Tote: You gain 3 life.
        Ability ability = new SimpleActivatedAbility(new GainLifeEffect(3), new ManaCostsImpl<>("{W}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private TinkersTote(final TinkersTote card) {
        super(card);
    }

    @Override
    public TinkersTote copy() {
        return new TinkersTote(this);
    }
}
