package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.MentorAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.target.TargetPlayer;

/**
 *
 * @author TheElk801
 */
public final class TruefireCaptain extends CardImpl {

    public TruefireCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{R}{W}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Mentor
        this.addAbility(new MentorAbility());

        // Whenever Truefire Captain is dealt damage, it deals that much damage to target player.
        Ability ability = new DealtDamageToSourceTriggeredAbility(
                new TruefireCaptainEffect(),
                false, false, true
        );
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public TruefireCaptain(final TruefireCaptain card) {
        super(card);
    }

    @Override
    public TruefireCaptain copy() {
        return new TruefireCaptain(this);
    }
}

class TruefireCaptainEffect extends OneShotEffect {

    public TruefireCaptainEffect() {
        super(Outcome.Damage);
        this.staticText = "it deals that much damage to target player";
    }

    public TruefireCaptainEffect(final TruefireCaptainEffect effect) {
        super(effect);
    }

    @Override
    public TruefireCaptainEffect copy() {
        return new TruefireCaptainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = (Integer) getValue("damage");
        return new DamageTargetEffect(amount).apply(game, source);
    }
}
