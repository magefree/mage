package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.keyword.ManifestDreadEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnidentifiedHovership extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature with toughness 5 or less");

    static {
        filter.add(new ToughnessPredicate(ComparisonType.FEWER_THAN, 6));
    }

    public UnidentifiedHovership(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}{W}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Unidentified Hovership enters, exile up to one target creature with toughness 5 or less.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetForSourceEffect());
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);

        // When Unidentified Hovership leaves the battlefield, the exiled card's owner manifests dread.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new UnidentifiedHovershipEffect()));

        // Crew 1
        this.addAbility(new CrewAbility(1));
    }

    private UnidentifiedHovership(final UnidentifiedHovership card) {
        super(card);
    }

    @Override
    public UnidentifiedHovership copy() {
        return new UnidentifiedHovership(this);
    }
}

class UnidentifiedHovershipEffect extends OneShotEffect {

    UnidentifiedHovershipEffect() {
        super(Outcome.Benefit);
        staticText = "the exiled card's owner manifests dread";
    }

    private UnidentifiedHovershipEffect(final UnidentifiedHovershipEffect effect) {
        super(effect);
    }

    @Override
    public UnidentifiedHovershipEffect copy() {
        return new UnidentifiedHovershipEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source, -1));
        if (exileZone == null || exileZone.isEmpty()) {
            return false;
        }
        for (Card card : exileZone.getCards(game)) {
            Player player = game.getPlayer(card.getOwnerId());
            if (player != null) {
                ManifestDreadEffect.doManifestDread(player, source, game);
            }
        }
        return true;
    }
}
