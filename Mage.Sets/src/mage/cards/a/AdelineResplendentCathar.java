package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetPowerSourceEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.constants.*;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.permanent.token.HumanToken;

/**
 *
 * @author weirddan455
 */
public final class AdelineResplendentCathar extends CardImpl {

    public AdelineResplendentCathar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Adeline, Resplendent Cathar's power is equal to the number of creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerSourceEffect(
                CreaturesYouControlCount.instance, Duration.EndOfGame)).addHint(CreaturesYouControlHint.instance)
        );

        // Whenever you attack, for each opponent, create a 1/1 white Human creature token that's tapped and attacking that player or a planeswalker they control.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new AdelineResplendentCatharEffect(), 1));
    }

    private AdelineResplendentCathar(final AdelineResplendentCathar card) {
        super(card);
    }

    @Override
    public AdelineResplendentCathar copy() {
        return new AdelineResplendentCathar(this);
    }
}

class AdelineResplendentCatharEffect extends OneShotEffect {

    public AdelineResplendentCatharEffect() {
        super(Outcome.Benefit);
        staticText = "for each opponent, create a 1/1 white Human creature token that's tapped and attacking that player or a planeswalker they control";
    }

    private AdelineResplendentCatharEffect(final AdelineResplendentCatharEffect effect) {
        super(effect);
    }

    @Override
    public AdelineResplendentCatharEffect copy() {
        return new AdelineResplendentCatharEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            new HumanToken().putOntoBattlefield(1, game, source, source.getControllerId(), true, true, opponentId);
        }
        return true;
    }
}
