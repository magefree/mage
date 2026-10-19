package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.effects.keyword.HeistEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.target.common.TargetOpponent;

import java.util.UUID;

public final class ImpetuousLootmonger extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell you don't own");

    static {
        filter.add(ImpetuousLootmongerPredicate.instance);
    }

    public ImpetuousLootmonger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(FirstStrikeAbility.getInstance());
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscardControllerEffect(1));
        ability.addEffect(new HeistEffect().concatBy(", then"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
        this.addAbility(new SpellCastControllerTriggeredAbility(new CreateTokenEffect(new TreasureToken(), 1, true), filter, false));
    }

    private ImpetuousLootmonger(final ImpetuousLootmonger card) {
        super(card);
    }

    @Override
    public ImpetuousLootmonger copy() {
        return new ImpetuousLootmonger(this);
    }
}

enum ImpetuousLootmongerPredicate implements ObjectSourcePlayerPredicate<StackObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<StackObject> input, Game game) {
        return input.getObject() instanceof Spell && !((Spell) input.getObject()).getCard().isOwnedBy(input.getPlayerId());
    }
}
