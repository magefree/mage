package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterAttackingOrBlockingCreature;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class SigridGodFavored extends CardImpl {

    private static final FilterCreaturePermanent filterGod = new FilterCreaturePermanent("God creatures");
    static {
        filterGod.add(SubType.GOD.getPredicate());
    }

    public SigridGodFavored(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // protection from God creatures
        this.addAbility(new ProtectionAbility(filterGod));

        // When Sigrid, God-Favored enters the battlefield, exile up to one target attacking or blocking creature until Sigrid leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SigridGodFavoredExileEffect());
        ability.addTarget(new TargetPermanent(0,1,new FilterAttackingOrBlockingCreature(), false));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        this.addAbility(ability);
    }

    private SigridGodFavored(final SigridGodFavored card) {
        super(card);
    }

    @Override
    public SigridGodFavored copy() {
        return new SigridGodFavored(this);
    }
}

class SigridGodFavoredExileEffect extends OneShotEffect {

    SigridGodFavoredExileEffect() {
        super(Outcome.Exile);
        this.staticText = "exile up to one target attacking or blocking creature until {this} leaves the battlefield. <i>(That creature returns under its owner's control.)</i>";
    }

    SigridGodFavoredExileEffect(final SigridGodFavoredExileEffect effect) {
        super(effect);
    }

    @Override
    public SigridGodFavoredExileEffect copy() {
        return new SigridGodFavoredExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        // If Sigrid, God-Favored leaves the battlefield before its triggered ability resolves, the target won't be exiled.
        if (sourcePermanent != null) {
            return new ExileTargetEffect(CardUtil.getCardExileZoneId(game, source), sourcePermanent.getIdName()).apply(game, source);
        }
        return false;
    }
}
