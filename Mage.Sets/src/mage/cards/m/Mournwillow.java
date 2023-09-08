package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class Mournwillow extends CardImpl {

    public Mournwillow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");
        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.SKELETON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // <i>Delirium</i> &mdash; When Mournwillow enters the battlefield, if there are four or more card types among cards in your graveyard,
        // creatures with power 2 or less can't block this turn.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new MournwillowEffect(), false),
                DeliriumCondition.instance,
                "<i>Delirium</i> &mdash; When {this} enters the battlefield, if there are four or more card types among cards in your graveyard, "
                        + "creatures with power 2 or less can't block this turn.");
        ability.addHint(CardTypesInGraveyardHint.YOU);
        this.addAbility(ability);
    }

    private Mournwillow(final Mournwillow card) {
        super(card);
    }

    @Override
    public Mournwillow copy() {
        return new Mournwillow(this);
    }
}

class MournwillowEffect extends RestrictionEffect {

    public MournwillowEffect() {
        super(Duration.EndOfTurn);
        staticText = "creatures with power 2 or less can't block this turn";
    }

    private MournwillowEffect(final MournwillowEffect effect) {
        super(effect);
    }

    @Override
    public MournwillowEffect copy() {
        return new MournwillowEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getPower().getValue() <= 2;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }
}