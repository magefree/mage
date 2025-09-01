package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.effects.common.combat.CantBlockCreaturesSourceEffect;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

/**
 * @author TheElk801
 */
public final class SpiritWorldToken extends TokenImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(Predicates.not(SubType.SPIRIT.getPredicate()));
    }

    public SpiritWorldToken() {
        super("Spirit Token", "1/1 colorless Spirit creature token with \"This token can't block or be blocked by non-Spirit creatures.\"");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SPIRIT);
        power = new MageInt(1);
        toughness = new MageInt(1);

        Ability ability = new SimpleStaticAbility(new CantBlockCreaturesSourceEffect(filter).setText("this token can't block"));
        ability.addEffect(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield).setText("or be blocked by non-Spirit creatures"));
        this.addAbility(ability);
    }

    private SpiritWorldToken(final SpiritWorldToken token) {
        super(token);
    }

    public SpiritWorldToken copy() {
        return new SpiritWorldToken(this);
    }
}
