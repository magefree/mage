package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.token.PlantToken;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AudienceWithTrostani extends CardImpl {

    public AudienceWithTrostani(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Create a 0/1 green Plant creature token, then draw cards equal to the number of differently named creature tokens you control.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new PlantToken()));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(AudienceWithTrostaniValue.instance)
                .setText(", then draw cards equal to the number of differently named creature tokens you control"));
        this.getSpellAbility().addHint(AudienceWithTrostaniValue.getHint());
    }

    private AudienceWithTrostani(final AudienceWithTrostani card) {
        super(card);
    }

    @Override
    public AudienceWithTrostani copy() {
        return new AudienceWithTrostani(this);
    }
}

enum AudienceWithTrostaniValue implements DynamicValue {
    instance;
    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(TokenPredicate.TRUE);
    }

    private static final Hint hint = new ValueHint(
            "Differently named creature tokens you control", instance
    );

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return CardUtil.differentlyNamedAmongCollection(
                game.getBattlefield().getActivePermanents(
                        filter, sourceAbility.getControllerId(), sourceAbility, game
                ), game
        );
    }

    @Override
    public AudienceWithTrostaniValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "1";
    }
}
