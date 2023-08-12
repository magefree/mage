package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.MyrToken;
import mage.util.ManaUtil;

import java.util.UUID;

/**
 * @author Loki, North
 */
public final class Myrsmith extends CardImpl {

    public Myrsmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever you cast an artifact spell, you may pay {1}. If you do, put a 1/1 colorless Myr artifact creature token onto the battlefield.
        this.addAbility(new SpellCastControllerTriggeredAbility(new MyrsmithEffect(), StaticFilters.FILTER_SPELL_AN_ARTIFACT, false));
    }

    private Myrsmith(final Myrsmith card) {
        super(card);
    }

    @Override
    public Myrsmith copy() {
        return new Myrsmith(this);
    }
}

class MyrsmithEffect extends CreateTokenEffect {

    public MyrsmithEffect() {
        super(new MyrToken());
        staticText = "you may pay {1}. If you do, create a 1/1 colorless Myr artifact creature token";
    }

    public MyrsmithEffect(final MyrsmithEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Cost cost = ManaUtil.createManaCost(1, false);
        if (cost.pay(source, game, source, source.getControllerId(), false, null)) {
            super.apply(game, source);
        }
        return true;
    }

    @Override
    public MyrsmithEffect copy() {
        return new MyrsmithEffect(this);
    }
}
