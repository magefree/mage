package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.OxToken;
import mage.target.common.TargetOpponent;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author Xanderhall
 */
public final class OxDrover extends CardImpl {

    public OxDrover(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Ox Drover can't be blocked by Oxen.
        this.addAbility(new SimpleStaticAbility(new CantBeBlockedByCreaturesSourceEffect(new FilterCreaturePermanent(SubType.OX, "Oxen"), Duration.WhileOnBattlefield)));

        // Whenever Ox Drover enters the battlefield or attacks, target opponent creates a 2/4 white Ox creature token and you draw a card.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new CreateTokenTargetEffect(new OxToken()));
        ability.addEffect(new DrawCardSourceControllerEffect(1).setText("and you draw a card"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private OxDrover(final OxDrover card) {
        super(card);
    }

    @Override
    public OxDrover copy() {
        return new OxDrover(this);
    }
}
