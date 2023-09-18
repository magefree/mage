package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.permanent.token.TokenImpl;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WakerootElemental extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledLandPermanent();

    public WakerootElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // {G}{G}{G}{G}{G}: Untap target land you control. It becomes a 5/5 Elemental creature with haste. It's still a land.
        Ability ability = new SimpleActivatedAbility(
                new UntapTargetEffect(), new ManaCostsImpl<>("{G}{G}{G}{G}{G}")
        );
        ability.addEffect(new BecomesCreatureTargetEffect(
                new WakerootElementalToken(), false, true, Duration.Custom
        ).setText("It becomes a 5/5 Elemental creature with haste. It's still a land."));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private WakerootElemental(final WakerootElemental card) {
        super(card);
    }

    @Override
    public WakerootElemental copy() {
        return new WakerootElemental(this);
    }
}

class WakerootElementalToken extends TokenImpl {

    WakerootElementalToken() {
        super("", "5/5 Elemental creature with haste");
        this.cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(HasteAbility.getInstance());
    }

    private WakerootElementalToken(final WakerootElementalToken token) {
        super(token);
    }

    public WakerootElementalToken copy() {
        return new WakerootElementalToken(this);
    }
}
