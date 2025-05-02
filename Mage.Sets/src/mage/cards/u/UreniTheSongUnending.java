package mage.cards.u;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.hint.common.LandsYouControlHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.target.common.TargetPermanentAmount;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UreniTheSongUnending extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("creatures and/or planeswalkers your opponents control");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public UreniTheSongUnending(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Protection from white and from black
        this.addAbility(ProtectionAbility.from(ObjectColor.WHITE, ObjectColor.BLACK));

        // When Ureni enters, it deals X damage divided as you choose among any number of target creatures and/or planeswalkers your opponents control, where X is the number of lands you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageMultiEffect("it"));
        ability.addTarget(new TargetPermanentAmount(LandsYouControlCount.instance, 0, filter));
        this.addAbility(ability.addHint(LandsYouControlHint.instance));
    }

    private UreniTheSongUnending(final UreniTheSongUnending card) {
        super(card);
    }

    @Override
    public UreniTheSongUnending copy() {
        return new UreniTheSongUnending(this);
    }
}
