package mage.cards.t;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TriumphantChomp extends CardImpl {

    public TriumphantChomp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Triumphant Chomp deals damage to target creature equal to 2 or the greatest power among Dinosaurs you control, whichever is greater.
        this.getSpellAbility().addEffect(new DamageTargetEffect(TriumphantChompValue.instance)
                .setText("{this} deals damage to target creature equal to 2 or the greatest power among Dinosaurs you control, whichever is greater"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(TriumphantChompValue.getHint());
    }

    private TriumphantChomp(final TriumphantChomp card) {
        super(card);
    }

    @Override
    public TriumphantChomp copy() {
        return new TriumphantChomp(this);
    }
}

enum TriumphantChompValue implements DynamicValue {
    instance;
    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.DINOSAUR);
    private static final Hint hint = new ValueHint("Current damage", instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int power = game.getBattlefield()
                .getActivePermanents(filter, sourceAbility.getControllerId(), sourceAbility, game)
                .stream()
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .max()
                .orElse(0);
        return Math.max(2, power);
    }

    @Override
    public TriumphantChompValue copy() {
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
