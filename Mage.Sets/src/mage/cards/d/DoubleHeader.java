package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;
import java.util.regex.Pattern;

/**
 * @author L_J
 */
public final class DoubleHeader extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanent with a two-word name");

    static {
        filter.add(new DoubleHeaderPredicate());
    }

    public DoubleHeader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.subtype.add(SubType.DRAKE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Double Header enters the battlefield, you may return target permanent with a two-word name to its owner’s hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect(), true);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private DoubleHeader(final DoubleHeader card) {
        super(card);
    }

    @Override
    public DoubleHeader copy() {
        return new DoubleHeader(this);
    }
}

class DoubleHeaderPredicate implements Predicate<Permanent> {

    public DoubleHeaderPredicate() {
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        String name = input.getName();
        return hasTwoWords(name);
    }

    private boolean hasTwoWords(String str) {
        return Pattern.compile("\\s+").split(str).length == 2;
    }

    @Override
    public String toString() {
        return "";
    }
}
