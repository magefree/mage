package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.SubLayer;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TokenImpl;
import mage.target.TargetPermanent;

/**
 *
 * @author TheElk801
 */
public final class SkilledAnimator extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("artifact you control");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public SkilledAnimator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When Skilled Animator enters the battlefield, target artifact you control becomes an artifact creature with base power and toughness 5/5 for as long as Skilled Animator remains on the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SkilledAnimatorBecomesCreatureEffect(), false);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private SkilledAnimator(final SkilledAnimator card) {
        super(card);
    }

    @Override
    public SkilledAnimator copy() {
        return new SkilledAnimator(this);
    }
}

class SkilledAnimatorBecomesCreatureEffect extends BecomesCreatureTargetEffect {

    public SkilledAnimatorBecomesCreatureEffect() {
        super(new SkilledAnimatorToken(), false, false, Duration.WhileOnBattlefield);
        this.staticText = "target artifact you control becomes an artifact creature with base power and toughness 5/5 for as long as {this} remains on the battlefield";
    }

    public SkilledAnimatorBecomesCreatureEffect(final SkilledAnimatorBecomesCreatureEffect effect) {
        super(effect);
    }

    @Override
    public SkilledAnimatorBecomesCreatureEffect copy() {
        return new SkilledAnimatorBecomesCreatureEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent == null) {
            this.discard();
            return false;
        }
        return super.apply(layer, sublayer, source, game);
    }
}

class SkilledAnimatorToken extends TokenImpl {

    public SkilledAnimatorToken() {
        super("", "5/5 artifact creature as long as {this} is on the battlefield");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        power = new MageInt(5);
        toughness = new MageInt(5);
    }

    public SkilledAnimatorToken(final SkilledAnimatorToken token) {
        super(token);
    }

    public SkilledAnimatorToken copy() {
        return new SkilledAnimatorToken(this);
    }
}
