package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenControllerTargetPermanentEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactOrEnchantmentCard;
import mage.game.permanent.token.GolemToken;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CavalierOfDawn extends CardImpl {

    private static final FilterCard filter
            = new FilterArtifactOrEnchantmentCard("artifact or enchantment card from your graveyard");

    public CavalierOfDawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}{W}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Cavalier of Dawn enters the battlefield, destroy up to one target nonland permanent. Its controller creates a 3/3 colorless Golem artifact creature token.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addEffect(new CreateTokenControllerTargetPermanentEffect(new GolemToken()));
        ability.addTarget(new TargetNonlandPermanent(0, 1, false));
        this.addAbility(ability);

        // When Cavalier of Dawn dies, return target artifact or enchantment card from your graveyard to your hand.
        ability = new DiesSourceTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private CavalierOfDawn(final CavalierOfDawn card) {
        super(card);
    }

    @Override
    public CavalierOfDawn copy() {
        return new CavalierOfDawn(this);
    }
}