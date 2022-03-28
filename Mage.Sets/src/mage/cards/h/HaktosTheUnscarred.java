package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;
import mage.util.RandomUtil;

/**
 * @author TheElk801
 */
public final class HaktosTheUnscarred extends CardImpl {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(HaktosTheUnscarredPredicate.instance);
    }

    public HaktosTheUnscarred(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{R}{W}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(1);

        // Haktos the Unscarred attacks each combat if able.
        this.addAbility(new AttacksEachCombatStaticAbility());

        // As Haktos enters the battlefield, choose 2, 3, or 4 at random.
        this.addAbility(new AsEntersBattlefieldAbility(new HaktosTheUnscarredChooseEffect()));

        // Haktos has protection from each converted mana cost other than the chosen number.
        Ability ability = new ProtectionAbility(filter);
        ability.setRuleVisible(false);
        this.addAbility(new SimpleStaticAbility(
                new GainAbilitySourceEffect(ability, Duration.WhileOnBattlefield)
                        .setText("{this} has protection from each mana value other than the chosen number")
        ));
    }

    private HaktosTheUnscarred(final HaktosTheUnscarred card) {
        super(card);
    }

    @Override
    public HaktosTheUnscarred copy() {
        return new HaktosTheUnscarred(this);
    }
}

class HaktosTheUnscarredChooseEffect extends OneShotEffect {

    HaktosTheUnscarredChooseEffect() {
        super(Outcome.Benefit);
        staticText = "choose 2, 3, or 4 at random";
    }

    private HaktosTheUnscarredChooseEffect(final HaktosTheUnscarredChooseEffect effect) {
        super(effect);
    }

    @Override
    public HaktosTheUnscarredChooseEffect copy() {
        return new HaktosTheUnscarredChooseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (permanent == null) {
            permanent = game.getPermanent(source.getSourceId());
        }
        if (controller == null || permanent == null) {
            return false;
        }
        int number = 2 + RandomUtil.nextInt(3);
        game.informPlayers(permanent.getLogName() + ": " + controller.getLogName() + " has chosen " + number + " at random");
        game.getState().setValue(permanent.getId() + "" + (permanent.getZoneChangeCounter(game) + 1) + "_haktos_number", number);
        permanent.addInfo("chosen number", CardUtil.addToolTipMarkTags("Chosen number: " + number), game);
        return true;
    }
}

enum HaktosTheUnscarredPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        Object obj = game.getState().getValue(input.getSourceId() + "" + input.getSource().getSourceObjectZoneChangeCounter() + "_haktos_number");
        if (!(obj instanceof Integer)) {
            return false;
        }
        int num = (int) obj;
        return input.getObject().getManaValue() != num;
    }
}
