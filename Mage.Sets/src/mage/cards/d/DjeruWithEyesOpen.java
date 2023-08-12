
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class DjeruWithEyesOpen extends CardImpl {

    private static final FilterCard filter = new FilterCard("planeswalker card");

    static {
        filter.add(CardType.PLANESWALKER.getPredicate());
    }

    public DjeruWithEyesOpen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Djeru, With Eyes Open enters the battlefield, you may search your library for a planeswalker card, reveal it, put it into your hand, then shuffle your library.
        Effect effect = new SearchLibraryPutInHandEffect(new TargetCardInLibrary(0, 1, filter), true);
        effect.setText("you may search your library for a planeswalker card, reveal it, put it into your hand, then shuffle");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect, true));

        // If a source would deal damage to a planeswalker you control, prevent 1 of that damage.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DjeruWithEyesOpenPreventEffect()));
    }

    private DjeruWithEyesOpen(final DjeruWithEyesOpen card) {
        super(card);
    }

    @Override
    public DjeruWithEyesOpen copy() {
        return new DjeruWithEyesOpen(this);
    }
}

class DjeruWithEyesOpenPreventEffect extends PreventionEffectImpl {

    public DjeruWithEyesOpenPreventEffect() {
        super(Duration.WhileOnBattlefield, 1, false, false);
        this.staticText = "If a source would deal damage to a planeswalker you control, prevent 1 of that damage";
    }

    public DjeruWithEyesOpenPreventEffect(DjeruWithEyesOpenPreventEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGE_PERMANENT) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && permanent.isPlaneswalker(game) && permanent.isControlledBy(source.getControllerId())) {
                return super.applies(event, source, game);
            }
        }
        return false;
    }

    @Override
    public DjeruWithEyesOpenPreventEffect copy() {
        return new DjeruWithEyesOpenPreventEffect(this);
    }
}
