package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.permanent.ProtectedByOpponentPredicate;
import mage.game.permanent.token.Elemental11BlueRedToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JoyfulStormsculptor extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell that has convoke");
    private static final FilterPermanent filter2 = new FilterPermanent();

    static {
        filter.add(new AbilityPredicate(ConvokeAbility.class));
        filter2.add(CardType.BATTLE.getPredicate());
        filter2.add(ProtectedByOpponentPredicate.instance);
    }

    public JoyfulStormsculptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Joyful Stormsculptor enters the battlefield, create two 1/1 blue and red Elemental creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new Elemental11BlueRedToken(), 2)));

        // Whenever you cast a spell that has convoke, Joyful Stormsculptor deals 1 damage to each opponent and each battle they protect.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new DamagePlayersEffect(1, TargetController.OPPONENT), filter, false
        );
        ability.addEffect(new DamageAllEffect(1, filter2).setText("and each battle they protect"));
        this.addAbility(ability);
    }

    private JoyfulStormsculptor(final JoyfulStormsculptor card) {
        super(card);
    }

    @Override
    public JoyfulStormsculptor copy() {
        return new JoyfulStormsculptor(this);
    }
}
