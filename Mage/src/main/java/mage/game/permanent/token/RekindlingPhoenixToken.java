package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 * @author LevelX2
 */
public final class RekindlingPhoenixToken extends TokenImpl {

    public RekindlingPhoenixToken() {
        super("Elemental Token", "0/1 red Elemental creature token with \"At the beginning of your upkeep, sacrifice this creature and return target card named Rekindling Phoenix from your graveyard to the battlefield. It gains haste until end of turn.\"");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ELEMENTAL);
        color.setRed(true);
        power = new MageInt(0);
        toughness = new MageInt(1);

        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new SacrificeSourceEffect(), TargetController.YOU, false);
        ability.addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect().setText("and return target card named Rekindling Phoenix from your graveyard to the battlefield"));
        ability.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn).setText("It gains haste until end of turn"));
        FilterCard filter = new FilterCard("card named Rekindling Phoenix from your graveyard");
        filter.add(new NamePredicate("Rekindling Phoenix"));
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }


    private RekindlingPhoenixToken(final RekindlingPhoenixToken token) {
        super(token);
    }

    public RekindlingPhoenixToken copy() {
        return new RekindlingPhoenixToken(this);
    }
}
