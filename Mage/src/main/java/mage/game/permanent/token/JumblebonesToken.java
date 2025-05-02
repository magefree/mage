package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 * @author TheElk801
 */
public final class JumblebonesToken extends TokenImpl {

    private static final FilterCard filter = new FilterCard("card named Ozox, the Clattering King from your graveyard");

    static {
        filter.add(new NamePredicate("Ozox, the Clattering King"));
    }

    public JumblebonesToken() {
        super("Jumblebones", "Jumblebones, a legendary 2/1 black Skeleton creature token with \"This creature can't block\" and \"When this creature leaves the battlefield, return target card named Ozox, the Clattering King from your graveyard to your hand.\"");
        supertype.add(SuperType.LEGENDARY);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SKELETON);
        color.setBlack(true);
        power = new MageInt(2);
        toughness = new MageInt(1);

        // This creature can't block
        this.addAbility(new CantBlockAbility());

        // When this creature leaves the battlefield, return target card named Ozox, the Clattering King from your graveyard to your hand.
        Ability ability = new LeavesBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private JumblebonesToken(final JumblebonesToken token) {
        super(token);
    }

    public JumblebonesToken copy() {
        return new JumblebonesToken(this);
    }
}
